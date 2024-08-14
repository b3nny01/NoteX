// css
import './TagInput.css';

function TagInput(props)
{
    // parameters
    const tag=props.tag;
    const onNameChange=props.onNameChange;
    const onValueChange= props.onValueChange;

    // render
    return (
        <div className="tag-input">
            <input type="text" name="name" className="name" placeholder="tag name" value={tag.name} onChange={onNameChange}/>
            <input type="text" name="value" className="value" placeholder="tag value" value={tag.value} onChange={onValueChange}/>
        </div>
    );
}

// export
export default TagInput;