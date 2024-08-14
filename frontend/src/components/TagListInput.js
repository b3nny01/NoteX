// css
import './TagListInput.css';

// data
import icons from '../data/icons';

// components
import TagInput from './TagInput';
import IconButton from './IconButton';


function TagListInput(props)
{
    // parameters
    const tags=props.tags;
    const onNameChange=props.onNameChange;
    const onValueChange=props.onValueChange;
    const onNewTag=props.onNewTag;
    const onDeleteTag=props.onDeleteTag;

    // variables
    const tagInputs=[];


    // logic
    for(let i=0;i<tags.length;i++)
    {
        tagInputs[i]=<TagInput key={i} tag={tags[i]} onNameChange={(e)=>{onNameChange(i,e)}} onValueChange={(e)=>{onValueChange(i,e)}}/>;
    }

    // render
    return (
        <div className="tag-list-input">
            {tagInputs}
            <div className="controls">
                <IconButton icon={icons["add"]} onClick={onNewTag}/>
                <IconButton icon={icons["remove"]} onClick={onDeleteTag}/>
            </div>
        </div>
    );

}

// exports
export default TagListInput;