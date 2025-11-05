package ua.snake.game.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.snake.game.model.Direction;
import ua.snake.game.model.GameState;
import ua.snake.game.service.GameService;

/**
 * Контролер для гри Змійка
 * @author Сергій Щербаков
 */
@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @MessageMapping("/game/start/{sessionId}")
    @SendTo("/topic/game/{sessionId}")
    public GameState startGame(@DestinationVariable String sessionId) {
        return gameService.createGame(sessionId);
    }

    @MessageMapping("/game/direction/{sessionId}")
    public void changeDirection(@DestinationVariable String sessionId, String direction) {
        try {
            Direction dir = Direction.valueOf(direction.toUpperCase());
            gameService.changeDirection(sessionId, dir);
        } catch (IllegalArgumentException e) {
            // Ігноруємо невірні напрямки
        }
    }

    @MessageMapping("/game/restart/{sessionId}")
    @SendTo("/topic/game/{sessionId}")
    public GameState restartGame(@DestinationVariable String sessionId) {
        gameService.restartGame(sessionId);
        return gameService.getGame(sessionId);
    }
}
