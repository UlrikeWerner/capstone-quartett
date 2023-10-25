import {useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {GameStateDTO} from "../../types/GameStateDTO.ts";
import "./Game.scss";
import ScoreBoard from "./Component/ScoreBoard/ScoreBoard.tsx";
import Card from "./Component/Card/Card.tsx";
import Info from "./Component/Info/Info.tsx";
import {GAME_INFO_TEXTS, getStartText} from "../../types/Game_Info_Texts.ts";

export default function Game() {
    const {id} = useParams();
    const [errorMessage, setErrorMessage] = useState<string>();
    const [gameState, setGameState]= useState<GameStateDTO>();
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
                            <section className="deck player-deck">
                                <Card type="deck" owner="player" score={gameState.score.player}/>
                                <Card type="playCard" owner="player" score={gameState.score.player} />
                            </section>
                            <section className="deck opponent-deck">
                                <Card type="deck" owner="opponent" score={gameState.score.opponent}/>
                            </section>
                        </section>
            }
        </>
    )
}