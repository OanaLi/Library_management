package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator {
    private Cache<Book> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache) {
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<Book> findAll() {
        if(cache.hasResult()){
            return cache.load();
        }
        List<Book> books = decoratedRepository.findAll(); //bookRepository-ul original se ocupa de asta fiindca Cache nu e disponibil
        cache.save(books);
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        //?? de ce nu foloseste cache? findById e mai eficient facut din mysql?
        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Book book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        cache.invalidateCache();
        return decoratedRepository.delete(book);
    }

    @Override
    public boolean updateStock(Book book) {
        return decoratedRepository.updateStock(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
