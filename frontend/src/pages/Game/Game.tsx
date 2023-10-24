import {useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {GameStateDTO} from "../../types/GameStateDTO.ts";
import "./Game.scss";
import ScoreBoard from "./Component/ScoreBoard.tsx";

export default function Game() {
    const {id} = useParams();
    const [errorMessage, setErrorMessage] = useState<string>();
    const [gameState, setGameState]= useState<GameStateDTO>();

    useEffect(() => {
        getGame();
    });

    function getGame() {
        axios.get("/api/game/" + id)
            .then(response => {
                setGameState(response.data);
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
                        </section>
            }
        </>
    )
}