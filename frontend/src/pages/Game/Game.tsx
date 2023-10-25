import {useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {GameStateDTO} from "../../types/GameStateDTO.ts";
import "./Game.scss";
import ScoreBoard from "./Component/ScoreBoard/ScoreBoard.tsx";
import CardDeck from "./Component/CardDeck/CardDeck.tsx";
import Info from "./Component/Info/Info.tsx";
import {GAME_INFO_TEXTS, getStartText} from "../../types/Game_Info_Texts.ts";

export default function Game() {
    const {id} = useParams();
    const [errorMessage, setErrorMessage] = useState<string>();
    const [gameState, setGameState]= useState<GameStateDTO>();
    const [isPlayerDeckVisible, setIsPlayerDeckVisible] = useState<boolean>(true);
    const [isOpponentDeckVisible, setIsOpponentDeckVisible] = useState<boolean>(true);
    const [infoText, setInfoText] = useState<string>("");
    const [instructionText, setInstructionText] = useState<string>("");

    useEffect(() => {
        getGame();
    });

    useEffect(() => {
        setInfoText(getStartText(gameState?.nextTurnBy));
    },[gameState]);

    function getGame() {
        axios.get("/api/game/" + id)
            .then(response => {
                setGameState(response.data);
                setInstructionText(GAME_INFO_TEXTS.startTurnInfo);
            })
            .catch(() => {
                setErrorMessage("Das Spiel konnte nicht geladen werden!");
            })
    }

    return(
        <>
            {
                errorMessage
                    ? <p>{errorMessage}</p>
                    : gameState &&
                        <section className="gameBoard">
                            <ScoreBoard playerScore={gameState.score.player} opponentScore={gameState.score.opponent}/>
                            <section className="infoField">
                                <Info infoText={infoText} instructionText={instructionText} nextTurn={gameState.nextTurnBy}/>
                            </section>
                            <CardDeck side="player" isVisible={isPlayerDeckVisible}/>
                            <CardDeck side="opponent" isVisible={isOpponentDeckVisible}/>
                        </section>
            }
        </>
    )
}