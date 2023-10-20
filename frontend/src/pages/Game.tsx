import {useParams} from "react-router-dom";

export default function Game() {
    const {id} = useParams();

    return(
        <>
            <h2>Game</h2>
            <p>GameId: {id}</p>
        </>
    )
}