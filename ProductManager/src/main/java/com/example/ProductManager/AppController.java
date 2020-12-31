package com.example.ProductManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private ProductService service;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private RoleService roleService;

    public AppController() {
    }

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
        model.addAttribute("checked",false);


        var roleList=roleService.listAll();
        if(roleList.size()==0){
            Role admin=new Role();
            admin.setName("ADMIN");
            Role userr=new Role();
            userr.setName("USER");
            roleService.save(admin);
            roleService.save(userr);
        }

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    String signup(@ModelAttribute("user") User user) {
        if(user.isEnabled()==true)
            userService.signUpUser(user,1);
        else
            userService.signUpUser(user,2);


        return "redirect:/login";
    }

}
