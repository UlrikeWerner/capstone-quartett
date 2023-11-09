import "../../App.scss";

type BackdropProps = {
    readonly open: boolean;
}

export default function Backdrop(props: BackdropProps) {
    return (
        <div className={props.open ? "backdrop-active" : ""}></div>
    )
}