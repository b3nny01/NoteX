// css
import './CarouselDiv.css';

// components
import React from 'react';

// assets
import img0 from '../../assets/images/welcomeImage0.jpg'
import img1 from '../../assets/images/welcomeImage1.jpg'
import img2 from '../../assets/images/welcomeImage2.jpg'

class CarouselDiv extends React.Component{
    constructor()
    {
        super();
        this.state={
            images:[
                img0,
                img1,
                img2
            ],
            currentIndex:0,
            currentImage:img0
        }
        this.changeImage=this.changeImage.bind(this);
    }

    componentDidMount()
    {
        this.setState({intervalId:setInterval(()=>{this.changeImage()},5000)});
    }
    componentWillUnmount()
    {
        clearInterval(this.state.intervalId);
    }

    changeImage()
    {
        let newCurrentIndex=(this.state.currentIndex + 1)% this.state.images.length;
        let newCurrentImg=(this.state.images[newCurrentIndex]);
        this.setState({currentIndex: newCurrentIndex, currentImage:newCurrentImg});
    }

    render()
    {
        
        return (
            <div className="carousel-div">
                <img src={this.state.currentImage} alt="img"/>
            </div>
        );
    }
}

// export
export default CarouselDiv;