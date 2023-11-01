import {CardDTO} from "./CardDTO.ts";

export type GameStateDTO = {
    score: {
        opponent: number;
        player: number;
    };
    nextScore?: {
        opponent: number;
        player: number;
    }
    actualTurn?: "PLAYER" | "OPPONENT"
    nextTurnBy?: "PLAYER" | "OPPONENT";
    nextPlayerCard?: CardDTO;
    nextOpponentCard?: CardDTO;
    category?: string;
    turnWinner?: "PLAYER" | "OPPONENT" | "DRAW";
    playerCard?: CardDTO;
    opponentCard?: CardDTO;
}