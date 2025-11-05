package ua.snake.game.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.snake.game.model.Direction;
import ua.snake.game.model.GameState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Сервіс для управління грою
 * @author Сергій Щербаков
 */
@Service
@EnableScheduling
public class GameService {

    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, GameState> games = new ConcurrentHashMap<>();

    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public GameState createGame(String sessionId) {
        GameState gameState = new GameState();
        games.put(sessionId, gameState);
        return gameState;
    }

    public GameState getGame(String sessionId) {
        return games.get(sessionId);
    }

    public void changeDirection(String sessionId, Direction direction) {
        GameState gameState = games.get(sessionId);
        if (gameState != null && !gameState.isGameOver()) {
            gameState.getSnake().changeDirection(direction);
        }
    }

    public void restartGame(String sessionId) {
        GameState gameState = new GameState();
        games.put(sessionId, gameState);
    }

    @Scheduled(fixedRate = 150)
    public void updateGames() {
        games.forEach((sessionId, gameState) -> {
            if (!gameState.isGameOver()) {
                gameState.update();
                messagingTemplate.convertAndSend("/topic/game/" + sessionId, gameState);
            }
        });
    }

    public void removeGame(String sessionId) {
        games.remove(sessionId);
    }
}
