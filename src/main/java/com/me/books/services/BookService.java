package com.me.books.services;

import com.me.books.entities.Book;
import com.me.books.entities.Chapter;
import com.me.books.entities.Genre;
import com.me.books.entities.User;
import com.me.books.repos.BookRepository;
import com.me.books.repos.GenreRepository;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.swing.event.DocumentEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.xml.bind.v2.util.EditDistance.editDistance;

@Service
public class BookService {
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    FileService fileService;
    private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private static final String CHAPTER_PATTERN = "<title>";

    public List<Genre> getGenres() {

        return genreRepository.findAll();
    }

    public String addBook(Book book, User user) throws ParserConfigurationException, IOException, SAXException{
        List<String> chapters = splitBookIntoChapters(new String(book.getFile().getBytes(), StandardCharsets.UTF_8));
        book.setPath(String.format("%s/%s/%s", FileService.BOOK_STORAGE, user.getId(), book.getName()));
        book.setSize(book.getFile().getSize());
        book.setUploader(user);
        int i;
        for(i = 0; i < chapters.size(); i++ ){
            fileService.uploadBook(user.getId(), book.getName(), i, chapters.get(i).getBytes(StandardCharsets.UTF_8));
        }
        book.setChapterCount(i);
        bookRepository.save(book);

        return "";
    }
    public static List<String> splitBookIntoChapters(String fileContent) throws IOException {
        StringBuilder currentChapter = new StringBuilder();



        String[] chapterContentArray = fileContent.split(CHAPTER_PATTERN);
        List<String> chapters = Arrays.stream(chapterContentArray).toList();

        return chapters;
    }
    public Book getBookById(long id) {
        return bookRepository.getReferenceById(id);
    }

    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findByGenres(genre);

    }

    public List<Book> searchBooksByName(String name) {
        return bookRepository.findByNameContaining(name);

    }

    public List<Book> getBooks(long amount) {
        if (amount > 0) {
            return bookRepository.findAll(Pageable.ofSize((int) amount)).stream().toList();
        }
        return bookRepository.findAll();

    }

    public Chapter getChapter(Book book, int chapterId) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder dBuilder = dbf.newDocumentBuilder();
        String chapterXml = fileService.loadChapter(chapterId, book.getPath());
        Chapter chapter = new Chapter("А", chapterXml, chapterId);
        return chapter;
    }

    private String innerXml(String chpater, Node node) {
        DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
        LSSerializer lsSerializer = lsImpl.createLSSerializer();
        NodeList childNodes = node.getChildNodes();
        StringBuilder sb = new StringBuilder();
        sb.append(chpater).append("\n");
        for (int i = 0; i < childNodes.getLength(); i++) {
            sb.append(lsSerializer.writeToString(childNodes.item(i)));
        }
        return sb.toString();
    }

}
