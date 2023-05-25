package com.bookStoreManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStoreManagement.entity.MyBookList;
import com.bookStoreManagement.repository.MyBookRepository;

@Service
public class MyBookListService {
	
	@Autowired
	MyBookRepository myRepository;
	
	public void saveMyBook(MyBookList book)
	{
		myRepository.save(book);
	}
	
	public List<MyBookList> getAllMyBooks()
	{
		return myRepository.findAll();
	}
	
	public void deleteById(int id)
	{
		myRepository.deleteById(id);
	}
}
