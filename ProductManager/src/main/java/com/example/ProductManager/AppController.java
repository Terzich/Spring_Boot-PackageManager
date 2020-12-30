package com.example.ProductManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private ProductService service;

    @Autowired
    private UserDetailsServiceImpl userService;


    @RequestMapping("/")
    public String viewHomePage(Model model){

        List<Product> productList=service.listAll();
        model.addAttribute("productList",productList);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewProductForm(Model model){
        Product product=new Product();
        model.addAttribute("product",product);

        return "new_product";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product){
        service.save(product);
        return "redirect:/";
    }

    @RequestMapping("/edit/{Id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "Id") Long Id){
        ModelAndView mav=new ModelAndView("edit_product");
        Product product=service.get(Id);
        mav.addObject("product",product);
        return mav;
    }
    @RequestMapping("/delete/{Id}")
    public String deleteProduct(@PathVariable(name = "Id") Long Id){
       service.delete(Id);
       return "redirect:/";
    }

    @RequestMapping("/403")
    public String accessDenied(){
        return "403";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    String signup(@ModelAttribute("user") User user) {


        userService.signUpUser(user);

        return "redirect:/login";
    }


}
