import "./Card.scss";

type CardDeckProps = {
    type: "deck" | "playCard";
    owner: "player" | "opponent";
    score: number;
}

export default function Card(props: CardDeckProps) {

    return (
        <>
            {
             (props.type === "deck")
                    ? <div className={"card card-" + (props.score > 1 ? props.owner : "isEmpty")}></div>
                    : <div className={"card card-absolute card-" +props.owner}></div>
            }
        </>
    )
}