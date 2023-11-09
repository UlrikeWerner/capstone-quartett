import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {OpenGameDTO} from "../../types/OpenGameDTO.ts";
import {useNavigate} from "react-router-dom";
import "./gameOverview.scss";
import BasicButton from "../../Component/BasicButton.tsx";

export default function GameOverview() {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string | undefined>(undefined);
    const [gameList, setGameList] = useState<OpenGameDTO[]>([]);
    const [idToEdit, setIdToEdit] = useState<string>("");
    const [titleValue, setTitleValue] = useState<string>();

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

    function submitNewTitle(event: FormEvent, gameId: string, newTitle: string) {
        event.preventDefault();
        axios.patch(`/api/game/${gameId}`, {
            title: newTitle
        })
            .then(() => {
                setTitleValue("");
                setIdToEdit("");
                getGameList();
            })
            .catch((error) => {
                console.error(error);
            })
    }

    function deleteGame(gameId: string) {
        if (!window.confirm("Wirklich löschen?")) {
            return;
        }
        axios.delete("/api/game/" + gameId)
            .then(() => {
                getGameList();
            }).catch(() => {
            setErrorMessage("Beim Löschen ist was schief gelaufen!");
        })
    }

    function startEditing(id: string, title: string) {
        setIdToEdit(id);
        setTitleValue(title);
    }

    function cancelEditing() {
        setIdToEdit("");
        setTitleValue("");
    }

    return (
        <section className="game-overview">
            <h1 className="headline">Offene Spiele</h1>
            {errorMessage
                ?
                <p className="error">{errorMessage}</p>
                :
                <div className="game-list-wrapper">
                    {
                        gameList.map((game) =>
                            <div className="game-element-wrapper" key={game.gameId}>
                                {idToEdit === game.gameId
                                    ?
                                    <form className="edit-form">
                                        <input className="input-title"
                                               type="text"
                                               value={titleValue}
                                               onChange={event => setTitleValue(event.target.value)}
                                        />
                                        <button className="button icon-button"
                                                type="submit"
                                                title="Speichern"
                                                onClick={(event: FormEvent) => {
                                                    if (titleValue) {
                                                        submitNewTitle(event, game.gameId, titleValue)
                                                    }
                                                }}
                                        >
                                            check
                                        </button>

                                        <BasicButton icon={true}
                                                     tooltip="Abbrechen"
                                                     text="close"
                                                     buttonClick={() => cancelEditing()}
                                        />
                                    </form>
                                    :
                                    <span className="game-title">{game.title}</span>
                                }
                                <div className="button-wrapper">
                                    <BasicButton text="Spielen"
                                                 buttonClick={() => navigate('/game/' + game.gameId)}
                                    />
                                    <BasicButton text="edit"
                                                 icon={true}
                                                 tooltip="bearbeiten"
                                                 buttonClick={() => startEditing(game.gameId, game.title)}
                                    />
                                    <BasicButton text="delete"
                                                 icon={true}
                                                 tooltip="löschen"
                                                 functionValue={game.gameId}
                                                 buttonClick={(value: string) => deleteGame(value)}
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