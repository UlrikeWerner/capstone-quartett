import "./ScoreBoard.scss"

type ScoreBoardProps = {
    readonly playerScore: number;
    readonly opponentScore: number;
}

export default function ScoreBoard(props: ScoreBoardProps) {

    return (
        <div className="score flex-row justify-content-space-between">
            <div className="playerScore flex-row justify-content-space-between">
                <p>Spieler</p>
                <p>{props.playerScore}</p>
            </div>
            <div className="opponentScore flex-row justify-content-space-between">
                <p>{props.opponentScore}</p>
                <p>Gegner</p>
            </div>
        </div>
    );
}