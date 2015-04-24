var React = require('react');
var App = require('./components/App.jsx');

window.renderOnClient = function (initialData) {
    React.render(
        <App data={initialData} />,
        document.getElementById('content')
    );
};

window.renderOnServer = function (initialData) {
    //initialData = Java.from(initialData); Listの場合
    return React.renderToString(
        <App data={initialData} />
    );
};

