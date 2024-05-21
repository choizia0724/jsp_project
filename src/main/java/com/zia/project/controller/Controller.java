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
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        List<Post> posts = postService.getAllPosts();
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

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("bodyItem", "Member");
        model.addAttribute("direct", "login");
        return "index";
    }

    @PostMapping("/login")
    public String login(User user,HttpServletRequest request, HttpServletResponse response,Model model) {
        model.addAttribute("bodyItem", "Member");
        UserDetails userDetails = new MyUserDetails(user.getUsername(), user.getPassword());
        String token = jwtUtil.generateToken(userDetails);
        HttpSession session = request.getSession();
        session.setAttribute("jwtToken", token);
        return "redirect:/";
    }
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


    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("bodyItem", "Member");
        model.addAttribute("direct", "signup");
        return "index";
    }


    @PostMapping("/signup")
    public String signup(User user, HttpServletResponse response) {
        userService.createUser(user);
        UserDetails userDetails = new MyUserDetails(user.getUsername(), user.getPassword());
        String token = jwtUtil.generateToken(userDetails);
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return "redirect:/";
    }

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

    @PostMapping("/post/{id}")
    public String updatePostById(@ModelAttribute Post post, @PathVariable("id") String id) {

        postService.updatePost(id, post);

        return "redirect:/";
    }

    @PostMapping("/post")
    public String post(Post post) {
        postService.savePost(post);
        return "redirect:/";
    }


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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);
    }
}