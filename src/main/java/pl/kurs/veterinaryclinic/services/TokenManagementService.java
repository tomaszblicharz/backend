package pl.kurs.veterinaryclinic.services;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.model.Token;
import pl.kurs.veterinaryclinic.model.Visit;
import pl.kurs.veterinaryclinic.repository.TokenRepository;

@Service
public class TokenManagementService {

    private static final Long TIME_OF_LIVE_TOKEN_IN_MINUTES = 5L;

    private final TokenRepository tokenRepository;

    public TokenManagementService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token add(Visit visit) {
        Instant tokenThreshold = Instant.now().plusSeconds(100 * TIME_OF_LIVE_TOKEN_IN_MINUTES);
        Token token = new Token();
        token.setValue(generateToken());
        token.setStatus(false);
        token.setVisit(visit);
        token.setInstantTo(tokenThreshold);
        return tokenRepository.save(token);
    }

    public void updateStateByToken(long tokenId) {
        tokenRepository.updateStateByToken(tokenId);
    }

    public Token findByValue(String token) {
        return tokenRepository.findByValue(token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
