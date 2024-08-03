package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Quote;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface QuotesRepository extends JpaRepository<Quote, Integer>
{
    Page<QuoteResponse> findQuoteByQuoteLinesTextContaining(String query, Pageable pageable);
    Optional<QuoteResponse> findQuoteById(int id);
    List<Quote> findQuotesByIdIn(List<Integer> ids);
}