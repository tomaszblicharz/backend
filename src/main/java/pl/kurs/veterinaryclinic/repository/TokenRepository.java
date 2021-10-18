package pl.kurs.veterinaryclinic.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.veterinaryclinic.model.Token;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Modifying
    @Query("update Token t set t.status = true where t.id = ?1")
    void updateStateByToken(long tokenId);

    Token findByValue(String token);

}
