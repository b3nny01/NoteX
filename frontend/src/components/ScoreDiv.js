// css
import './ScoreDiv.css';

// data
import icons from '../data/icons';

// components
import IconDiv from './IconDiv';


function ScoreDiv(props)
{
    // parameter
    const score=props.score;

    // variables
    const intScore= Math.round(score);
    const iconDivs=[];

    // logic
    for(let i=1;i<=5;i++)
    {
        if(i<=intScore){
            iconDivs[i]=<IconDiv icon={icons["starLight"]} key={i}/>;
        }else{
            iconDivs[i]=<IconDiv icon={icons["starBlack"]} key={i}/>;
        }
    }

    // render
    return (
        <div className="score-div">
            {iconDivs}
        </div>
    );
}

// export
export default ScoreDiv;
