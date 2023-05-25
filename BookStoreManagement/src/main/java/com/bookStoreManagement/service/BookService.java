package com.bookStoreManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStoreManagement.entity.Book;
import com.bookStoreManagement.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository repository;
	
	public void save(Book book)
	{
		repository.save(book);
	}
	
	public List<Book> getAllBooks()
	{
		return repository.findAll();
	}
	
	public Book getBookById(int id)
	{
		return repository.findById(id).get();
	}
	
	public void deleteById(int id)
	{
		repository.deleteById(id);
	}
}
