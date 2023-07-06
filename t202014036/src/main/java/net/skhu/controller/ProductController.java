package net.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.skhu.entity.Product;
import net.skhu.model.Pagination;
import net.skhu.repository.CategoryRepository;
import net.skhu.repository.ProductRepository;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired ProductRepository productRepository;
    @Autowired CategoryRepository categoryRepository;

    @RequestMapping("list")
    public String list(Model model, Pagination pagination) {
        List<Product> products = productRepository.findByTitleStartsWith(pagination);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/edit";
    }

    @PostMapping("create")
    public String create(Model model, Product product, Pagination pagination) {
        productRepository.save(product);
        pagination.setTitle(null);
        int lastPage = (int)Math.ceil((double)productRepository.count() / pagination.getSz());
        pagination.setPg(lastPage);
        return "redirect:list?" + pagination.getQueryString();
    }

    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id, Pagination pagination) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Product product, Pagination pagination) {
        productRepository.save(product);
        return "redirect:list?" + pagination.getQueryString();
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, @RequestParam("id") int id, Pagination pagination) {
        productRepository.deleteById(id);
        return "redirect:list?" + pagination.getQueryString();
    }
    @RequestMapping("list2")
    public String list2(Model model) {
        List<Product> products = productRepository.findByCategoryId(2);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/list2";
    }

}
