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
                                <div>
                                    <BasicButton text="Spielen"
                                                    buttonClick={() => navigate('/game/' + game.gameId)}
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