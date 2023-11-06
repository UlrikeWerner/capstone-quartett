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
    const [gameState, setGameState] = useState<GameStateDTO>();
    const [runningGameState, setRunningGameState] = useState<GameStateDTO>();
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
    const [opponentCardIsClickable, setOpponentCardIsClickable] = useState<boolean>(false);
    const [showInfoButton, setShowInfoButton] = useState<boolean>(false);
    const [continueButton, setContinueButton] = useState<boolean>(true);

    useEffect(() => {
        getGame();
    }, [id]);

    useEffect(() => {
        setInfoText(getStartText(gameState?.nextTurnBy));
        setPlayerDeckIsClickable(true);
        setPlayerCardIsVisible(false);
        setOpponentCardIsVisible(false);
        setPlayerCardIsLaidOut(false);
        setOpponentCardIsLaidOut(false);
        if (gameState?.score) {
            setCardsInDeckPlayer(gameState.score.player);
            setCardsInDeckOpponent(gameState.score.opponent);
        }
        if (gameState?.nextTurnBy === "PLAYER") {
            setCanChooseCategory(true);
        } else {
            setCanChooseCategory(false);
        }
    }, [gameState]);

    function getGame() {
        axios.get("/api/game/" + id)
            .then(response => {
                if (!response.data.playerCard) {
                    setRunningGameState({
                        ...response.data,
                        playerCard: response.data.nextPlayerCard,
                        actualTurn: response.data.nextTurnBy === "PLAYER" ? "OPPONENT" : "PLAYER"
                    });
                }
                setGameState(response.data);
                return response;
            })
            .then((response) => {
                if(response.data.finished){
                    let winner: string;
                    if(response.data.score.opponent === 0){
                        winner = "Du hast";
                    } else {
                        winner = "Der Gegner hat";
                    }
                    setInstructionText(GAME_INFO_TEXTS.gameOver + `\n ${winner} dieses Spiel gewonnen.`);
                    setPlayerDeckIsClickable(false);
                    setCanChooseCategory(false);
                } else {
                    setInstructionText(GAME_INFO_TEXTS.startTurnInfo);
                }
                setOpponentCardIsLaidOut(false);
                setOpponentCardIsVisible(false);
            })
            .catch(() => {
                setErrorMessage("Das Spiel konnte nicht geladen werden!");
            })
    }

    function getTurnResult(chosenCategory: TurnResultInputDTO) {
        axios.put("/api/game/" + id, chosenCategory)
            .then(response => {
                setRunningGameState({...response.data, actualTurn: "PLAYER"});
                return response;
            })
            .then(response => {
                let winner: string;
                if (response.data.turnWinner === "PLAYER") {
                    winner = "Du hast";
                } else if (response.data.turnWinner === "OPPONENT") {
                    winner = "Der Gegner hat";
                } else {
                    winner = "Keiner hat";
                }
                setInfoText(`Die Kategorie: ${chosenCategory.category} wurde gewählt. \n ${winner} diese Runde gewonnen.`);
                setInstructionText("");
                setShowInfoButton(true);
                setContinueButton(true);
                setOpponentCardIsLaidOut(false);
                setCanChooseCategory(false);
            })
            .catch(() => {
                setErrorMessage(GAME_INFO_TEXTS.gameOver);
            })
    }

    function getOpponentTurnResult() {
        axios.get("/api/game/" + id + "/opponentTurn")
            .then((response) => {
                if (gameState && runningGameState?.score) {
                    setRunningGameState(
                        {
                            ...runningGameState,
                            category: response.data.category,
                            nextPlayerCard: response.data.nextPlayerCard,
                            nextOpponentCard: response.data.opponentCard,
                            actualTurn: "OPPONENT",
                            nextTurnBy: response.data.nextTurnBy,
                            playerCard: response.data.playerCard,
                            opponentCard: undefined,
                            nextScore: response.data.score,
                            turnWinner: response.data.turnWinner
                        }
                    );
                }
                if(runningGameState && response.data.finished){
                    setRunningGameState(
                        {
                            ...runningGameState,
                            category: response.data.category,
                            nextPlayerCard: response.data.nextPlayerCard,
                            nextOpponentCard: response.data.opponentCard,
                            actualTurn: "OPPONENT",
                            nextTurnBy: response.data.nextTurnBy,
                            playerCard: response.data.playerCard,
                            opponentCard: undefined,
                            nextScore: response.data.score,
                            turnWinner: response.data.turnWinner,
                            "finished": response.data.finished,
                            "winner": response.data.winner
                        }
                    );
                }
                return response;
            })
            .then(response => {
                setInfoText(`Der Gegner ist am Zug! \n Die Kategorie: ${response.data.category} wurde gewählt.`);
                setInstructionText(GAME_INFO_TEXTS.seeOpponentCard);
                setOpponentCardIsLaidOut(true);
                setPlayerDeckIsClickable(false);
                setOpponentCardIsClickable(true);
                setCanChooseCategory(false);
            })
            .catch(() => {
                setErrorMessage(GAME_INFO_TEXTS.gameOver);
            })
    }

    function drawCard() {
        if (runningGameState?.actualTurn === "OPPONENT" && runningGameState?.nextTurnBy === "PLAYER") {
            setRunningGameState({
                ...runningGameState,
                "playerCard": runningGameState.nextPlayerCard
            });
            setCanChooseCategory(true);
            setInfoText(GAME_INFO_TEXTS.chooseCategory);
            setInstructionText(GAME_INFO_TEXTS.startChooseCategoryInfo);
            setOpponentCardIsLaidOut(true);
            setOpponentCardIsVisible(true);
            setPlayerDeckIsClickable(false);
            setPlayerCardIsVisible(true);
            setPlayerDeckIsClickable(false);
        } else if (runningGameState?.actualTurn === "PLAYER" && runningGameState?.nextTurnBy === "OPPONENT") {
            setRunningGameState({
                ...runningGameState,
                "playerCard": runningGameState.nextPlayerCard
            });
            setPlayerCardIsVisible(true);
            setPlayerCardIsLaidOut(false);
            getOpponentTurnResult();
        } else {
            setErrorMessage("Unerwarteter Zustand des Spiels!")
        }
    }

    function showOpponentCard() {
        setOpponentCardIsClickable(false);
        setOpponentCardIsLaidOut(false);
        setOpponentCardIsVisible(true);
        if (runningGameState?.nextScore) {
            setRunningGameState(
                {
                    ...runningGameState,
                    score: runningGameState?.nextScore,
                    nextScore: undefined,
                    opponentCard: runningGameState.nextOpponentCard,
                    nextOpponentCard: undefined,
                    actualTurn: "OPPONENT",
                    nextTurnBy: "PLAYER"
                })
        }
        let winner: string = "";
        switch (runningGameState?.turnWinner) {
            case "PLAYER":
                winner = "Du hast";
                break;
            case "OPPONENT":
                winner = "Der Gegner hat";
                break;
            case "DRAW":
                winner = "Keiner hat";
                break;
        }
        setInfoText(`${winner} diese Runde gewonnen.`);
        setInstructionText("");
        setShowInfoButton(true);
        setContinueButton(true);
    }

    function chooseCategory(category: string) {
        const selectedCategory: TurnResultInputDTO = {"category": category};
        getTurnResult(selectedCategory);
    }

    function clearPlayingCards() {
        setShowInfoButton(false);
        setCanChooseCategory(false);
        setOpponentCardIsVisible(false);
        setOpponentCardIsLaidOut(false);
        setOpponentCardIsClickable(false);
        setPlayerCardIsLaidOut(false);
        setPlayerCardIsVisible(false);
        if(runningGameState?.score){
            setCardsInDeckOpponent(runningGameState.score.opponent);
            setCardsInDeckPlayer(runningGameState.score.player);
        }
        if(runningGameState?.finished){
            let winner: string;
            if(runningGameState.winner === "PLAYER"){
                winner = "Du hast das Spiel gewonnen!"
            } else {
                winner = "Der Gegner hat das Spiel gewonnen!"
            }
            setInfoText(`${GAME_INFO_TEXTS.gameOver} \n ${winner}`);
            setInstructionText("");
            setPlayerDeckIsClickable(false);
            setShowInfoButton(true);
            setContinueButton(false);
        } else {
            setPlayerDeckIsClickable(true);
            setInfoText("");
            setInstructionText(GAME_INFO_TEXTS.resultChosenCategoryContinue);
        }
    }

    return (
        <>
            {
                errorMessage
                    ? <p>{errorMessage}</p>
                    : runningGameState &&
                    <section className="gameBoard">
                        <ScoreBoard playerScore={runningGameState.score.player}
                                    opponentScore={runningGameState.score.opponent}/>
                        <section className="infoField">
                            <Info infoText={infoText}
                                  instructionText={instructionText}
                                  showButton={showInfoButton}
                                  continueButton={continueButton}
                                  continueButtonClick={clearPlayingCards}
                            />
                        </section>

                        <section className="deck player-deck">
                            <Card type="deck"
                                  owner="player"
                                  score={runningGameState.score.player}
                                  cardsInDeck={cardsInDeckPlayer - 1}
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
                                  playCardIsClickable={opponentCardIsClickable}
                                  seeOpponentCard={showOpponentCard}
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