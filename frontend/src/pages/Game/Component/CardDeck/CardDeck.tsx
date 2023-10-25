import "./CardDeck.scss";

type DeckProps = {
    side: "player" | "opponent";
    isVisible: boolean
}

export default function CardDeck(props: DeckProps) {

    return (
        <div className={props.isVisible ? "deck deck-" + props.side : "deck is-empty deck-"+ props.side}></div>
    )
}