import "../../App.scss";

type BackdropProps = {
    open: boolean;
}

export default function Backdrop(props: BackdropProps) {
    return (
        <div className={props.open ? "backdrop-active" : ""}></div>
    )
}