import {useEffect, useState} from "react";
import axios from "axios";
import {OpenGameDTO} from "../../types/OpenGameDTO.ts";
import {useNavigate} from "react-router-dom";
import "./gameOverview.scss";
import BasicButton from "../../Component/BasicButton.tsx";

export default function GameOverview() {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string | undefined>(undefined);
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
        if(!window.confirm("Wirklich löschen?")){
            return;
        }
        axios.delete("/api/game/" + gameId)
            .then(() => {
                getGameList();
            }).catch(() => {
                setErrorMessage("Beim Löschen ist was schief gelaufen!");
        })
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
                                <span className="game-title">{game.title}</span>
                                <div className="button-wrapper">
                                    <BasicButton text="Spielen"
                                                 buttonClick={() => navigate('/game/' + game.gameId)}
                                    />
                                    <BasicButton text="close"
                                                 icon={true}
                                                 tooltip="löschen"
                                                 functionValue={game.gameId}
                                                 buttonClick={(value) => deleteGame(value)}
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