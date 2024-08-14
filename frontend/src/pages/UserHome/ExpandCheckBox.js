// css
import './ExpandCheckBox.css';

// hooks
import { useState } from 'react';

// data
import icons from '../../data/icons';

// components
import IconDiv from '../../components/IconDiv';


function ExpandCheckBox()
{
    // hooks
    const [isExpanded,setExpanded]=useState(false);

    // render
    return (
        <label className="expand-checkbox">
            <input type="checkbox" checked={isExpanded} onChange={()=>setExpanded(!isExpanded)}/>
            <IconDiv icon={icons["expand"]}/>
        </label>
    );
    
}

// export
export default ExpandCheckBox;