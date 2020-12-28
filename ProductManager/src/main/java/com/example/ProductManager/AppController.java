package com.example.ProductManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private ProductService service;

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


}
