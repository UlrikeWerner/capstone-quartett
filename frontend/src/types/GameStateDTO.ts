import {CardDTO} from "./CardDTO.ts";

export type GameStateDTO = {
    score: {
        opponent: number;
        player: number;
    };
    nextTurnBy?: "PLAYER" | "OPPONENT";
    nextPlayerCard?: CardDTO;
}