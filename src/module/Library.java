package module;

import java.util.ArrayList;

public class Library implements Room {
    private int readersInRoom = 0;

    private ArrayList<Book> books;

    public synchronized void enter(Reader reader) {
        int maxReadersInRoom = 2;
        while (readersInRoom >= maxReadersInRoom) {
            try {
                wait();
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        readersInRoom++;
        reader.setLocation("library");
        System.out.println("Reader " + reader.getName() + " entered to library");
        System.out.println("Readers in library room: " + readersInRoom);
        notify();
    }

    public synchronized void leave(Reader reader) {
        int minReadersInRoom = 0;
        while (readersInRoom <= minReadersInRoom) {
            try {
                wait();
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        readersInRoom--;
        reader.setLocation("near");
        System.out.println("Reader " + reader.getName() + " leaved library");
        System.out.println("Readers in library room: " + readersInRoom);
        notify();
    }


    synchronized void giveBook(Reader reader, Book book) {
        ArrayList<Book> readerBooks = reader.getBooks();
        readerBooks.add(book);
        reader.setBooks(readerBooks);
        books.remove(book);
        System.out.println("Reader " + reader.getName() + " has token the book "
                + book.getId());
    }

    synchronized void returnBook(Reader reader, Book book) {
        ArrayList<Book> readerBooks = reader.getBooks();
        readerBooks.remove(book);
        reader.setBooks(readerBooks);
        books.add(book);
        System.out.println("Reader " + reader.getName() + " has returned the book "
                + book.getId());
    }

    ArrayList<Book> getBooks() {
        return books;
    }

    void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}