import {useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {GameStateDTO} from "../../types/GameStateDTO.ts";
import "./Game.scss";
import {GAME_INFO_TEXTS, getStartText} from "../../types/Game_Info_Texts.ts";
import ScoreBoard from "./Component/ScoreBoard/ScoreBoard.tsx";
import Info from "./Component/Info/Info.tsx";
import Card from "./Component/Card/Card.tsx";

export default function Game() {
    const {id} = useParams();
    const [errorMessage, setErrorMessage] = useState<string>();
    const [gameState, setGameState]= useState<GameStateDTO>();
    const [infoText, setInfoText] = useState<string>("");
    const [instructionText, setInstructionText] = useState<string>("");
    const [playerDeckIsClickable, setPlayerDeckIsClickable] = useState<boolean>(false);
    const [playerCardIsVisible, setPlayerCardIsVisible] = useState<boolean>(false);
    const [opponentCardIsVisible, setOpponentCardIsVisible] = useState<boolean>(false);

    useEffect(() => {
        getGame();
    }, []);

    useEffect(() => {
        setInfoText(getStartText(gameState?.nextTurnBy));
        if(gameState?.nextTurnBy === "PLAYER"){
            setPlayerDeckIsClickable(true);
        }else {
            setPlayerDeckIsClickable(false);
        }
        setPlayerCardIsVisible(false);
        setOpponentCardIsVisible(false);
    },[gameState]);

    function getGame() {
        axios.get("/api/game/" + id)
            .then(response => {
                setGameState(response.data);
            })
            .then(() => {
                setInstructionText(GAME_INFO_TEXTS.startTurnInfo);
            })
            .catch(() => {
                setErrorMessage("Das Spiel konnte nicht geladen werden!");
            })
    }

    function drawCard() {
        setInfoText(GAME_INFO_TEXTS.chooseCategory);
        setInstructionText(GAME_INFO_TEXTS.startChooseCategoryInfo);
        setPlayerDeckIsClickable(false);
        setPlayerCardIsVisible(true);
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
                                <Info infoText={infoText} instructionText={instructionText} />
                            </section>
                            <section className="deck player-deck">
                                <Card type="deck" owner="player" score={gameState.score.player}/>
                                <Card type="movingCard" owner="player" score={gameState.score.player} isClickable={playerDeckIsClickable} drawCard={drawCard}/>
                            </section>
                            <section className="deck player-card">
                                <Card type="deck" owner="player"/>
                                <Card type="playCard" owner="player" isVisible={playerCardIsVisible} cardContent={gameState.nextPlayerCard}/>
                            </section>
                            <section className="deck opponent-card">
                                <Card type="deck" owner="opponent"/>
                                <Card type="playCard" owner="opponent" isVisible={opponentCardIsVisible}/>
                                <p className="vs">VS</p>
                            </section>
                            <section className="deck opponent-deck">
                                <Card type="deck" owner="opponent" score={gameState.score.opponent}/>
                            </section>
                        </section>
            }
        </>
    )
}