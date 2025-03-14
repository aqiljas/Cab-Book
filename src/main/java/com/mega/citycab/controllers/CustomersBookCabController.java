package com.mega.citycab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mega.citycab.models.CustomerBookCab;
import com.mega.citycab.models.CustomerBookCabDto;
import com.mega.citycab.services.CustomersBookCabRepository;

import jakarta.validation.Valid;




@Controller
@RequestMapping("/customerinterfacebookacab")
public class CustomersBookCabController {



    @GetMapping("/about")
    public String about(){
        return "customerinterfacebookacab/about";
    } 


    @GetMapping("/blog")
    public String blog(){
        return "customerinterfacebookacab/blog";
    } 
    @GetMapping("/contact")
    public String contact(){
        return "customerinterfacebookacab/contact";
    } 

    @GetMapping("/pricing")
    public String pricing(){
        return "customerinterfacebookacab/pricing";
    } 


    @GetMapping("/services")
    public String services(){
        return "customerinterfacebookacab/services";
    } 

    @GetMapping("/car")
    public String car(){
        return "customerinterfacebookacab/car";
    } 


    @GetMapping("/index")
    public String index(){
        return "customerinterfacebookacab/index";
    } 
    

    @Autowired
    private CustomersBookCabRepository repo;

    @GetMapping({"", "/"})
    public String showCustomersOrderFoodRepositoryList(Model model){
                                               // This parameter makes desending order of adminadd 
        List<CustomerBookCab> customerbookcab = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("customerbookcab", customerbookcab);
        return "customerinterfacebookacab/viewbookcab";
        
    }

    @GetMapping("/adminviewbookcab")
    public String showAnotherPage(Model model){
        // Fetch the data you need for the new page
        List<CustomerBookCab> customerbookcab = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    
        // Add the data to the model
        model.addAttribute("customerbookcab", customerbookcab);
        
        // Return the name of the new HTML file (e.g., anotherpage.html)
        return "customerinterfacebookacab/adminviewbookcab";
}


    // Create new product
    @GetMapping("/customerbookcab")
    public String showCustomerOrderForm(Model model){
        CustomerBookCabDto customerbookcabDto = new CustomerBookCabDto();
        model.addAttribute("customerbookcabDto", customerbookcabDto);
        // In the customerbookcabDto folder there is a customerbookcab called reservation
        return "customerinterfacebookacab/customerbookcab";
    }

    @PostMapping("/customerbookcab")
    public String addReservation(
        @Valid @ModelAttribute CustomerBookCabDto customerbookcabDto,
        BindingResult result,
        Model model) {
    
        // Check if there are validation errors
        if (result.hasErrors()) {
            // Return to the form with the validation errors
            return "customerinterfacebookacab/customerbookcab";
        }
    
        // If no errors, proceed with saving the data
        CustomerBookCab customerbookcab = new CustomerBookCab();
        customerbookcab.setName(customerbookcabDto.getName());
        customerbookcab.setType(customerbookcabDto.getType());
        customerbookcab.setPlace(customerbookcabDto.getPlace());
        customerbookcab.setPhone(customerbookcabDto.getPhone());
        customerbookcab.setDescription(customerbookcabDto.getDescription());
        customerbookcab.setTime(customerbookcabDto.getTime());
        customerbookcab.setStatus(customerbookcabDto.getStatus());
    
        repo.save(customerbookcab);
    
        return "redirect:/customerinterfacebookacab";
    }
    

    // customer update the details
    @GetMapping("/edit")
    public String showEditPage(
        Model model,
        @RequestParam int id
    ) {

        try {

            CustomerBookCab customerbookcab = repo.findById(id).get();
            model.addAttribute("customerbookcab", customerbookcab);

            CustomerBookCabDto customerbookcabDto = new CustomerBookCabDto();
            customerbookcabDto.setName(customerbookcab.getName());
            customerbookcabDto.setType(customerbookcab.getType());
            customerbookcabDto.setPlace(customerbookcab.getPlace());
            customerbookcabDto.setPhone(customerbookcab.getPhone());
            customerbookcabDto.setDescription(customerbookcab.getDescription());
            customerbookcabDto.setTime(customerbookcab.getTime());

            model.addAttribute("customerbookcabDto", customerbookcabDto);


        } catch(Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
            return "redirect:/customerinterfacebookacab";
        }

        return "customerinterfacebookacab/editbookcab";

    }

    // Post request for update the product 
    @PostMapping("/edit")
    public String updateProduct(
        Model model,
        @RequestParam int id,
        @Valid @ModelAttribute CustomerBookCabDto customerbookcabDto,
        BindingResult result
    ) {

        CustomerBookCab customerbookcab = repo.findById(id).get();
        model.addAttribute("customerbookcab", customerbookcab);
        // Check do we have any errors or not
        if(result.hasErrors()) {
            return "customerinterfacebookacab/editbookcab";
        }
        // Update the other details
        customerbookcab.setName(customerbookcabDto.getName());
        customerbookcab.setType(customerbookcabDto.getType());
        customerbookcab.setPlace(customerbookcabDto.getPlace());
        customerbookcab.setPhone(customerbookcabDto.getPhone());
        customerbookcab.setDescription(customerbookcabDto.getDescription());
        customerbookcab.setTime(customerbookcabDto.getTime());
        repo.save(customerbookcab);


        return "redirect:/customerinterfacebookacab";
    }

    // Admin update the order stauts
        @GetMapping("/admineditbookcab")
        public String showEditPageAdmin(
            Model model,
            @RequestParam int id
        ) {
    
            try {
    
                CustomerBookCab customerbookcab = repo.findById(id).get();
                model.addAttribute("customerbookcab", customerbookcab);
    
                CustomerBookCabDto customerbookcabDto = new CustomerBookCabDto();
                customerbookcabDto.setName(customerbookcab.getName());
                customerbookcabDto.setType(customerbookcab.getType());
                customerbookcabDto.setPlace(customerbookcab.getPlace());
                customerbookcabDto.setPhone(customerbookcab.getPhone());
                customerbookcabDto.setDescription(customerbookcab.getDescription());
                customerbookcabDto.setTime(customerbookcab.getTime());
                customerbookcabDto.setStatus(customerbookcab.getStatus());
    
                model.addAttribute("customerbookcabDto", customerbookcabDto);
    
    
            } catch(Exception ex) {
                System.out.println("Exception : " + ex.getMessage());
                return "redirect:/customerinterfacebookacab";
            }
    
            return "customerinterfacebookacab/admineditbookcab";
    
        }

    // Post request for update the product admin 
    @PostMapping("/admineditbookcab")
    public String updateProductAdmin(
        Model model,
        @RequestParam int id,
        @Valid @ModelAttribute CustomerBookCabDto customerbookcabDto,
        BindingResult result
    ) {

        CustomerBookCab customerbookcab = repo.findById(id).get();
        model.addAttribute("customerbookcab", customerbookcab);
        // Check do we have any errors or not
        if(result.hasErrors()) {
            return "customerinterfacebookacab/admineditbookcab";
        }
        // Update the other details
        customerbookcab.setName(customerbookcabDto.getName());
        customerbookcab.setType(customerbookcabDto.getType());
        customerbookcab.setPlace(customerbookcabDto.getPlace());
        customerbookcab.setPhone(customerbookcabDto.getPhone());
        customerbookcab.setDescription(customerbookcabDto.getDescription());
        customerbookcab.setTime(customerbookcabDto.getTime());
        customerbookcab.setStatus(customerbookcabDto.getStatus());
        repo.save(customerbookcab);


        return "redirect:/customerinterfacebookacab/adminviewbookcab";
    }



    // This controller for adminadd
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {

        try {
            CustomerBookCab customerbookcab = repo.findById(id).get();

            //Delete the product
            repo.delete(customerbookcab);
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/customerinterfacebookacab";
    }

    @GetMapping("/adminDelete")
    public String adminDeleteProduct(@RequestParam int id) {

        try {
            CustomerBookCab customerbookcab = repo.findById(id).get();

            //Delete the product
            repo.delete(customerbookcab);
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/customerinterfacebookacab/adminviewbookcab";
    }

}
