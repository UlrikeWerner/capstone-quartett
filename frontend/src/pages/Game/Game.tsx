import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {GameStateDTO} from "../../types/GameStateDTO.ts";
import "./Game.scss";
import {GAME_INFO_TEXTS, getStartText} from "../../types/Game_Info_Texts.ts";
import ScoreBoard from "./Component/ScoreBoard/ScoreBoard.tsx";
import Info from "./Component/Info/Info.tsx";
import Card from "./Component/Card/Card.tsx";
import {TurnResultInputDTO} from "../../types/TurnResultInputDTO.ts";

export default function Game() {
    const {id} = useParams();
    const [errorMessage, setErrorMessage] = useState<string>();
    const [gameState, setGameState]= useState<GameStateDTO>();
    const [runningGameState, setRunningGameState]= useState<GameStateDTO>();
    const [infoText, setInfoText] = useState<string>("");
    const [instructionText, setInstructionText] = useState<string>("");
    const [playerDeckIsClickable, setPlayerDeckIsClickable] = useState<boolean>(false);
    const [playerCardIsVisible, setPlayerCardIsVisible] = useState<boolean>(false);
    const [opponentCardIsVisible, setOpponentCardIsVisible] = useState<boolean>(false);
    const [playerCardIsLaidOut, setPlayerCardIsLaidOut] = useState<boolean>(false);
    const [opponentCardIsLaidOut, setOpponentCardIsLaidOut] = useState<boolean>(false);
    const [canChooseCategory, setCanChooseCategory] = useState<boolean>(false);
    const [cardsInDeckPlayer, setCardsInDeckPlayer] = useState<number>(0);
    const [cardsInDeckOpponent, setCardsInDeckOpponent] = useState<number>();

    useEffect(() => {
        getGame();
    }, []);

    useEffect(() => {
        setInfoText(getStartText(gameState?.nextTurnBy));
        setPlayerDeckIsClickable(true);
        setPlayerCardIsVisible(false);
        setOpponentCardIsVisible(false);
        setPlayerCardIsLaidOut(false);
        setOpponentCardIsLaidOut(false);
        if(gameState?.score.player){
            setCardsInDeckPlayer(gameState?.score.player);
        }
        if(gameState?.score.opponent){
            setCardsInDeckOpponent(gameState?.score.opponent);
        }
        if(gameState?.nextTurnBy === "PLAYER"){
            setCanChooseCategory(true);
        } else {
            setCanChooseCategory(false);
        }

    },[gameState]);

    function getGame() {
        axios.get("/api/game/" + id)
            .then(response => {
                if(!response.data.playerCard){
                    setRunningGameState({...response.data, playerCard: response.data.nextPlayerCard});
                }
                setGameState(response.data);
            })
            .then(() => {
                setInstructionText(GAME_INFO_TEXTS.startTurnInfo);
            })
            .catch(() => {
                setErrorMessage("Das Spiel konnte nicht geladen werden!");
            })
    }

    function getTurnResult(chosenCategory: TurnResultInputDTO){
        axios.put("/api/game/" + id, chosenCategory)
            .then(response => {
                setRunningGameState(response.data);
                return response;
            })
            .then(response => {
                let winner: string;
                if(response.data.turnWinner === "PLAYER"){
                    winner = "Du hast";
                } else if(response.data.turnWinner === "OPPONENT"){
                    winner = "Der Gegner hat";
                } else {
                    winner = "Keiner hat";
                }
                setInfoText(`Die Kategorie: ${chosenCategory.category} wurde gewählt. \n ${winner} diese Runde gewonnen.`);
                setInstructionText(GAME_INFO_TEXTS.resultChosenCategoryContinue);
                setOpponentCardIsLaidOut(false);
                setCardsInDeckOpponent(response.data.score.opponent);
                setPlayerDeckIsClickable(true);
                setCardsInDeckPlayer(response.data.score.player);
                setCanChooseCategory(false);
            })
            .catch(() => {
                setErrorMessage("Opps");
            })
    }

    function drawCard() {
        if(runningGameState?.nextTurnBy === "PLAYER"){
            setInfoText(GAME_INFO_TEXTS.chooseCategory);
            setInstructionText(GAME_INFO_TEXTS.startChooseCategoryInfo);
            setOpponentCardIsLaidOut(true);
        }else {
            if(runningGameState){
                setRunningGameState({...runningGameState, playerCard: runningGameState.nextPlayerCard});
            }
            setInfoText("");
            setInstructionText("");
            setOpponentCardIsLaidOut(true);
        }
        setPlayerDeckIsClickable(false);
        setPlayerCardIsVisible(true);
        setOpponentCardIsVisible(true);
        setPlayerDeckIsClickable(false);
    }

    function chooseCategory(category: string){
        const selectedCategory: TurnResultInputDTO = {"category": category};
        getTurnResult(selectedCategory);
    }

    return(
        <>
            {
                errorMessage
                    ? <p>{errorMessage}</p>
                    : runningGameState &&
                        <section className="gameBoard">
                            <ScoreBoard playerScore={runningGameState.score.player} opponentScore={runningGameState.score.opponent}/>
                            <section className="infoField">
                                <Info infoText={infoText} instructionText={instructionText} />
                            </section>

                            <section className="deck player-deck">
                                <Card type="deck"
                                      owner="player"
                                      score={runningGameState.score.player}
                                      cardsInDeck={cardsInDeckPlayer -1}
                                />
                                <Card type="movingCard"
                                      owner="player"
                                      score={runningGameState.score.player}
                                      cardsInDeck={cardsInDeckPlayer}
                                      isClickable={playerDeckIsClickable}
                                      drawCard={drawCard}/>
                            </section>

                            <section className="deck player-card">
                                <Card type="deck"
                                      owner="player"
                                />
                                <Card type="playCard"
                                      owner="player"
                                      isVisible={playerCardIsVisible}
                                      isLaidOut={playerCardIsLaidOut}
                                      cardContent={runningGameState.playerCard}
                                      canChooseCategory={canChooseCategory}
                                      selectCategory={chooseCategory}
                                />
                            </section>

                            <section className="deck opponent-card">
                                <Card type="deck"
                                      owner="opponent"
                                />
                                <Card type="playCard"
                                      owner="opponent"
                                      isVisible={opponentCardIsVisible}
                                      isLaidOut={opponentCardIsLaidOut}
                                      cardContent={runningGameState.opponentCard}
                                      canChooseCategory={canChooseCategory}
                                />
                                <p className="vs">VS</p>
                            </section>

                            <section className="deck opponent-deck">
                                <Card type="deck"
                                      owner="opponent"
                                      score={runningGameState.score.opponent}
                                      cardsInDeck={cardsInDeckOpponent}
                                />
                            </section>
                        </section>
            }
        </>
    )
}