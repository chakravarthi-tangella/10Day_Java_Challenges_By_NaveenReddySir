package com.bookStoreManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStoreManagement.entity.Book;
import com.bookStoreManagement.entity.MyBookList;
import com.bookStoreManagement.service.BookService;
import com.bookStoreManagement.service.MyBookListService;

@Controller
public class BookController {
	
	@Autowired
	BookService service;
	
	@Autowired
	MyBookListService myBookListService;
	
	@GetMapping("/")
	public String home()
	{
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister()
	{
		return "bookRegister";
	}
	
	@GetMapping("/available_books")
	public ModelAndView getAllBook()
	{
		List<Book> list = service.getAllBooks();
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("bookList");
//		mv.addObject("book", list);
		
		return new ModelAndView("bookList", "book", list);
	}
	
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book book)
	{
		service.save(book);
		return "redirect:/available_books";
	}
	
	@GetMapping("/my_books")
	public String getMyBooks(Model model)
	{
		List<MyBookList> list = myBookListService.getAllMyBooks();
		model.addAttribute("book",list);
		return "myBooks";
	}
	
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id)
	{
		Book b = service.getBookById(id);
		MyBookList mb = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookListService.saveMyBook(mb);
		return "redirect:/my_books";
	}
	
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id, Model model)
	{
		Book b = service.getBookById(id);
		model.addAttribute("book",b);
		return "bookEdit";
	}
	
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int id)
	{
		service.deleteById(id);
		return "redirect:/available_books";
	}
}
