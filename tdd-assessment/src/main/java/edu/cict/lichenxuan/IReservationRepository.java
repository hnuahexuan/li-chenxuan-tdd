package edu.cict.lichenxuan;

import java.util.*;
interface IReservationRepository {
    void save(Reservation reservation);

    boolean existsByUserAndBook(String userId, String bookId);

    List<Reservation> findByUser(String userId);

    List<Reservation> findByBook(String bookId);

    void delete(String userId, String bookId);
}

