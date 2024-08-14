// css
import './SearchTypeRadioDiv.css';

// components
import IconDiv from '../../components/IconDiv';

function SearchTypeRadioDiv(props)
{
    // parameters
    const searchTypes=props.searchTypes;
    const searchTypeChecked=props.searchTypeChecked;
    const onChange=props.onChange;

    // variables
    const searchTypesRadioLabels=[];

    // logic
    for(let i=0;i<searchTypes.length;i++)
    {
        searchTypesRadioLabels[i]=(
            <label className="search-type-radio-label" key={i}>
                <input onChange={onChange} type="radio" name="searchType" className="searchTypeRadio" value={searchTypes[i].value} checked={searchTypeChecked===searchTypes[i].value} />
                <IconDiv icon={searchTypes[i].icon}/>
            </label>
            );
    }

    // render
    return (
        <div className="search-type-radio-div">
            {searchTypesRadioLabels}
        </div>
    );
}

// export
export default SearchTypeRadioDiv;