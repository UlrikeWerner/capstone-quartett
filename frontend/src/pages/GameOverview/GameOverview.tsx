import {useEffect, useState} from "react";
import axios from "axios";
import {OpenGameDTO} from "../../types/OpenGameDTO.ts";
import {useNavigate} from "react-router-dom";
import "./gameOverview.scss";
import BasicButton from "../../Component/Buttons/BasicButton.tsx";
import Dialog from "../../Component/Dialog/Dialog.tsx";
import Backdrop from "../../Component/Dialog/Backdrop.tsx";

export default function GameOverview() {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string | undefined>(undefined);
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [idForDelete, setIdForDelete] = useState<string>("");
    const [gameList, setGameList] = useState<OpenGameDTO[]>([]);

    useEffect(() => {
        getGameList()
    }, []);

    function getGameList() {
        axios.get("/api/game")
            .then(response => {
                setGameList(response.data);
            })
            .catch(() => {
                setErrorMessage("Die Liste mit offenen Spielen konnte nicht geladen werden!");
            });
    }

    function deleteGame(gameId: string) {
        axios.delete("/api/game/" + gameId)
            .then(() => {
                const gameIndex = gameList.findIndex((game) => game.gameId === gameId);
                gameList.splice(gameIndex, 1);
            })
            .then(() => {
                setIdForDelete("");
                setIsOpen(false);
            })
            .catch(() => {
                setErrorMessage("Beim Löschen ist was schief gelaufen!");
            })
    }

    function openDialog(id: string) {
        setIdForDelete(id);
        setIsOpen(true);
    }

    return (
        <section className="game-overview">
            <h1 className="headline">Offene Spiele</h1>
            <div className="games-button-wrapper">
                <BasicButton
                    icon={true}
                    text="chevron_left"
                    tooltip="Lobby"
                    buttonClick={() => navigate('/lobby')}
                />
            </div>
            <Dialog open={isOpen}
                    content={`<p>Willst du das Spiel wirklich löschen?</p>`}
                    buttonName="Abbrechen"
                    buttonName2="Ok"
                    buttonFunction={() => setIsOpen(false)}
                    buttonFunction2={() => deleteGame(idForDelete)}
            />
            <Backdrop open={isOpen}/>
            {errorMessage
                ?
                <p className="error">{errorMessage}</p>
                :
                <div className="game-list-wrapper">
                    {
                        gameList.map((game) =>
                            <div className="game-element-wrapper" key={game.gameId}>
                                <span className="game-title">{game.title}</span>
                                <div className="button-wrapper">
                                    <BasicButton text="Spielen"
                                                 buttonClick={() => navigate('/game/' + game.gameId)}
                                    />
                                    <BasicButton text="close"
                                                 icon={true}
                                                 tooltip="löschen"
                                                 functionValue={game.gameId}
                                                 buttonClick={(id) => openDialog(id)}
                                    />
                                </div>
                            </div>
                        )
                    }
                </div>
            }
        </section>
    );
}