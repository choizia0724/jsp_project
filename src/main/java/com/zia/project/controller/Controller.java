package com.zia.project.controller;
import com.zia.project.model.Post;
import com.zia.project.model.User;
import com.zia.project.service.PostService;
import com.zia.project.service.UserService;
import com.zia.project.util.JwtUtil;
import com.zia.project.util.MyUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller implements WebMvcConfigurer {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;


    /**
     * 기본 게시판(메인화면)
     * */
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        List<Post> posts = postService.getAllPosts();
        //쿠키에 저장된 jwtToken를 불러옵니다.
        HttpSession session = request.getSession();
        String val = (String)session.getAttribute("jwtToken");
        if (val != null) {
            String userName = jwtUtil.getUsernameFromToken(val);
            model.addAttribute("userName", userName);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("bodyItem", "DashBoard");
        return "index";
    }

    /**
     * 로그인 정보 post 처리
     * */
    @PostMapping("/login")
    public String login(HttpServletRequest request,Model model, User user) {
        boolean isValidUser = userService.validateUser(user.getUsername(), user.getPassword());
        // alert로 알림메세지를 보내기
        if (!isValidUser) {
            model.addAttribute("response", "invalid username or password");
            model.addAttribute("bodyItem", "Member");
            model.addAttribute("direct", "login");
            return "index";
        }
        model.addAttribute("bodyItem", "Member");
        UserDetails userDetails = new MyUserDetails(user.getUsername(), user.getPassword());
        String token = jwtUtil.generateToken(userDetails);
        HttpSession session = request.getSession();
        session.setAttribute("jwtToken", token);
        return "redirect:/";
    }
    
    /**
     * 로그인 페이지
     * */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("bodyItem", "Member");
        model.addAttribute("direct", "login");
        return "index";
    }

    /**
     * 로그아웃 요청
     * */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.invalidate();
        System.out.println("쿠키 제거됨: ");
        try {
            Thread.sleep(500); // 0.5초 지연
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    /**
     * 회원가입 페이지
     * */
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("bodyItem", "Member");
        model.addAttribute("direct", "signup");
        return "index";
    }

    /**
     * 회원가입 정보 post 처리
     * */
    @PostMapping("/signup")
    public String signup(User user, HttpServletResponse response,Model model) {
        boolean exists = userService.checkUsernameExists(user.getUsername());
        if (exists) {
            model.addAttribute("response", "username exists");
            model.addAttribute("bodyItem", "Member");
            model.addAttribute("direct", "signup");
            return "index";
        }else{
            // username 중복검사후 유효할 때 계정을 만들고 토큰을 부여함
            userService.createUser(user);
            UserDetails userDetails = new MyUserDetails(user.getUsername(), user.getPassword());
            String token = jwtUtil.generateToken(userDetails);
            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            return "redirect:/";
        }
    }

    /**
     * 포스트 내부 페이지
     * */
    @GetMapping("/read/{id}")
    public String viewRead(Model model,HttpServletRequest request, @PathVariable("id") String id ) {
        HttpSession session = request.getSession();
        String val = (String)session.getAttribute("jwtToken");
        if (val!=null) {
            String userName = jwtUtil.getUsernameFromToken(val);
            model.addAttribute("loginUserName", userName);
            model.addAttribute("userName", userName);
        }
        Post post = postService.getPostById(id);
        model.addAttribute("id", post.getId());
        model.addAttribute("title", post.getTitle());
        model.addAttribute("content", post.getContent());
        model.addAttribute("author", post.getUsername());
        model.addAttribute("createdAt", post.getFormattedCreatedAt());
        model.addAttribute("bodyItem", "Read");
        return "index";
    }

    /**
     * 글작성 페이지
     * */
    @GetMapping("/post")
    public String viewPost(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String val = (String)session.getAttribute("jwtToken");
        if (val != null) {
            String userName = jwtUtil.getUsernameFromToken(val);
            model.addAttribute("loginUserName", userName);
            model.addAttribute("userName", userName);
        }
        model.addAttribute("bodyItem", "Write");
        return "index";
    }

    /**
     * 글 수정 페이지
     * */
    @GetMapping("/post/{id}")
    public String viewPostById(Model model,HttpServletRequest request, @PathVariable("id") String id ) {
        HttpSession session = request.getSession();
        String val = (String)session.getAttribute("jwtToken");
        if (val!=null) {
            String userName = jwtUtil.getUsernameFromToken(val);
            model.addAttribute("loginUserName", userName);
            model.addAttribute("userName", userName);
        }
        Post post = postService.getPostById(id);
        model.addAttribute("id",id);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("content", post.getContent());
        model.addAttribute("author", post.getUsername());
        model.addAttribute("bodyItem", "Write");
        return "index";
    }

    /**
     * 글 수정 post 처리
     * */
    @PostMapping("/post/{id}")
    public String updatePostById(@ModelAttribute Post post, @PathVariable("id") String id) {
        postService.updatePost(id, post);
        return "redirect:/";
    }
    
    /**
     * 글 작성 post 처리
     * */
    @PostMapping("/post")
    public String post(Post post) {
        postService.savePost(post);
        return "redirect:/";
    }

    /**
     * 게시글 삭제 요청
     * */
    @GetMapping("/delPost/{id}")
    public String delPost(HttpServletRequest request, @PathVariable("id") String id) {
        HttpSession session = request.getSession();
        String val = (String)session.getAttribute("jwtToken");
        if (val != null) {
            String userName = jwtUtil.getUsernameFromToken(val);
            Post post = postService.getPostById(id);

            String author = post.getUsername();
            if(author.equals(userName)) {postService.deletePost(id);}
        }
        return "redirect:/";
    }

    /**
     * 정적파일 처리
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);
    }
}