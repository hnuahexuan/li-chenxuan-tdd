package edu.cict.lichenxuan;

import java.util.List;
import java.util.Objects;

public class ReservationService {

    private final IBookRepository bookRepo;
    private final IReservationRepository reservationRepo;

    public ReservationService(IBookRepository bookRepo, IReservationRepository reservationRepo) {
        this.bookRepo = bookRepo;
        this.reservationRepo = reservationRepo;
    }

    public MemoryBookRepository casting_IBookRepository() {
        if (this.bookRepo instanceof MemoryBookRepository memBookRepo)
            return memBookRepo;
        else {
            System.out.println("IBookRepository implements mismatch");
            return null;
        }
    }

    public MemoryReservationRepository casting_IReservationRepository() {
        if (this.reservationRepo instanceof MemoryReservationRepository memReservRepo)
            return memReservRepo;
        else {
            System.out.println("IReservationRepository implements mismatch");
            return null;
        }
    }

    /**
     * Reserve a book for a user.
     * Throws IllegalArgumentException if book not found.
     * Throws IllegalStateException if no copies available or user already reserved.
     */
    public void reserve(String userId, String bookId) {
        // TODO: Implement using TDD
        MemoryBookRepository memBookRepo = casting_IBookRepository();
        MemoryReservationRepository memReservRepo = casting_IReservationRepository();

        if (memBookRepo == null || memReservRepo == null) {
        }else {
            Book book = memBookRepo.findById(bookId);
            // if book not found.
            if (book == null) {
                throw new IllegalArgumentException("The book: " + bookId + " is not found.");
               // if no copies available or user already reserved.
            } else if ((book.getCopiesAvailable() == 0) || memReservRepo.existsByUserAndBook(userId, bookId)) {
                throw new IllegalStateException("The book: " + bookId + " cannot be reserved.");
            } else {
                Reservation newRes = new Reservation();
                newRes.setBookId(bookId);
                newRes.setUserId(userId);
                memReservRepo.save(newRes);
            }
        }
    }

    /**
     * Cancel an existing reservation for a user.
     * Throws IllegalArgumentException if no such reservation exists.
     */
    public void cancel(String userId, String bookId) {
        // TODO: Implement using TDD
        MemoryBookRepository memBookRepo = casting_IBookRepository();
        MemoryReservationRepository memReservRepo = casting_IReservationRepository();

        if (memBookRepo == null || memReservRepo == null) {
        }else if (memReservRepo.existsByUserAndBook(userId, bookId)) {
            memReservRepo.delete(userId, bookId);
        }
    }

    /**
     * List all active reservations for a given user.
     */
    public List<Reservation> listReservations(String userId) {
        // TODO: Implement using TDD
        MemoryReservationRepository memReservRepo = casting_IReservationRepository();

        return memReservRepo.findByUser(userId);
    }

    /**
     * list all reservations for a book.
     */
    public List<Reservation> listReservationsForBook(String bookId) {
        // TODO: Implement using TDD
        MemoryReservationRepository memReservRepo = casting_IReservationRepository();

        return memReservRepo.findByUser(bookId);
    }
}

