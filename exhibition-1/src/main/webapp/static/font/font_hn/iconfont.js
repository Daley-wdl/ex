(function(window){var svgSprite='<svg><symbol id="icon-huaniao" viewBox="0 0 1024 1024"><path d="M281.567087 182.152428l84.452722 23.030173-23.030173 84.452722-84.452722-23.030173 23.030173-84.452722Z"  ></path><path d="M903.627211 241.057797l-1.464762-32.919723-32.290985-6.590137c-119.788777-24.473475-216.104382-7.745261-286.348434 49.606652-27.856275 22.74122-48.61667 49.627295-64.086548 77.112864-24.184479-43.470651-63.199778-85.14626-126.058954-109.125174l-30.114915 78.93801c46.60574 17.780125 79.319898 53.340375 97.253982 105.711411 7.164689 20.929836 10.594794 40.754435 12.231577 55.506124-103.352138 8.239822-177.30411-27.527714-220.137422-106.598181-17.924623-33.074542-26.031128-66.015768-29.526602-85.22883L238.337279 264.052749l0-80.966142c-59.844502 3.520417-109.128615 24.642056-109.128615 24.642056l7.140606 48.77837c0.206426 2.774704 5.280195 68.69672 41.974926 136.434421 66.60322 122.944508 178.626094 151.616164 268.619915 151.616164 9.347639 0 18.451008-0.313939 27.247319-0.859247l0 343.445755 84.486558 0L558.677989 535.573804c104.349862 12.654749 187.836116-7.075237 248.570829-58.868281C909.031261 389.919899 903.895564 247.100906 903.627211 241.057797zM751.990404 412.794436c-42.851375 36.250916-105.917836 49.112091-187.815474 38.323773 7.858795-39.778213 26.855971-97.027774 72.781367-134.526704 43.109407-35.199006 103.566305-47.97761 180.152783-38.262705C812.42666 315.168888 797.730017 374.078454 751.990404 412.794436z"  ></path></symbol><symbol id="icon-gengduo" viewBox="0 0 1024 1024"><path d="M516.297792 66.718097C272.858599 66.718097 74.750874 264.825822 74.750874 508.367343c0 243.439193 198.107725 441.649246 441.546917 441.649246 243.439193 0 441.546917-198.107725 441.546917-441.649246C957.947037 264.825822 759.839312 66.718097 516.297792 66.718097L516.297792 66.718097zM516.297792 888.005596c-209.363845 0-379.638253-170.274408-379.638253-379.638253 0-209.363845 170.274408-379.638253 379.638253-379.638253 209.261517 0 379.638253 170.274408 379.638253 379.638253C895.936045 717.62886 725.661637 888.005596 516.297792 888.005596L516.297792 888.005596z"  ></path><path d="M325.250724 450.244829c-32.02878 0-58.122514 25.991406-58.122514 58.122514 0 32.131108 25.991406 58.122514 58.122514 58.122514 32.02878 0 58.122514-25.991406 58.122514-58.122514C383.373239 476.236235 357.279504 450.244829 325.250724 450.244829L325.250724 450.244829z"  ></path><path d="M516.297792 450.244829c-32.02878 0-58.122514 25.991406-58.122514 58.122514 0 32.131108 25.991406 58.122514 58.122514 58.122514 32.131108 0 58.122514-25.991406 58.122514-58.122514C574.420306 476.236235 548.4289 450.244829 516.297792 450.244829L516.297792 450.244829z"  ></path><path d="M707.447187 450.244829c-32.02878 0-58.122514 25.991406-58.122514 58.122514 0 32.131108 25.991406 58.122514 58.122514 58.122514 32.02878 0 58.122514-25.991406 58.122514-58.122514C765.569701 476.236235 739.475967 450.244829 707.447187 450.244829L707.447187 450.244829z"  ></path></symbol><symbol id="icon-fanhui" viewBox="0 0 1024 1024"><path d="M532.526499 904.817574L139.506311 511.797385 532.526499 118.777197c12.258185-12.258185 12.432147-32.892131-0.187265-45.51052-12.707416-12.707416-32.995485-12.703323-45.511543-0.187265L75.166957 484.739123c-7.120165 7.120165-10.163477 17.065677-8.990768 26.624381-1.500167 9.755178 1.5104 20.010753 8.990768 27.491121l411.660734 411.660734c12.258185 12.258185 32.892131 12.432147 45.511543-0.187265 12.707416-12.707416 12.7023-32.995485 0.187265-45.51052z"  ></path></symbol></svg>';var script=function(){var scripts=document.getElementsByTagName("script");return scripts[scripts.length-1]}();var shouldInjectCss=script.getAttribute("data-injectcss");var ready=function(fn){if(document.addEventListener){if(~["complete","loaded","interactive"].indexOf(document.readyState)){setTimeout(fn,0)}else{var loadFn=function(){document.removeEventListener("DOMContentLoaded",loadFn,false);fn()};document.addEventListener("DOMContentLoaded",loadFn,false)}}else if(document.attachEvent){IEContentLoaded(window,fn)}function IEContentLoaded(w,fn){var d=w.document,done=false,init=function(){if(!done){done=true;fn()}};var polling=function(){try{d.documentElement.doScroll("left")}catch(e){setTimeout(polling,50);return}init()};polling();d.onreadystatechange=function(){if(d.readyState=="complete"){d.onreadystatechange=null;init()}}}};var before=function(el,target){target.parentNode.insertBefore(el,target)};var prepend=function(el,target){if(target.firstChild){before(el,target.firstChild)}else{target.appendChild(el)}};function appendSvg(){var div,svg;div=document.createElement("div");div.innerHTML=svgSprite;svgSprite=null;svg=div.getElementsByTagName("svg")[0];if(svg){svg.setAttribute("aria-hidden","true");svg.style.position="absolute";svg.style.width=0;svg.style.height=0;svg.style.overflow="hidden";prepend(svg,document.body)}}if(shouldInjectCss&&!window.__iconfont__svg__cssinject__){window.__iconfont__svg__cssinject__=true;try{document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>")}catch(e){console&&console.log(e)}}ready(appendSvg)})(window)