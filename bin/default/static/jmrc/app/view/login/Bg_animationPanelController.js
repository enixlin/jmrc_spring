Ext.define('jmrc.view.login.Bg_animationPanelController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login-bganimation',


    流浪星际:function(){
    	var container;
    	var camera, scene, renderer, particle;
    	var mouseX = 0, mouseY = 0;
    	 var windowHalfX = 300;
    	    var windowHalfY = 300;
//    	    var viewWidth = window.innerWidth;
//    	    var viewHeight = window.innerHeight;
    	    var viewWidth ;
    	    var viewHeight ;
    	init();
    	animate();
    	function init() {
    		container = document.createElement( 'div' );
    		viewWidth=container.width;
    		viewHeight=container.height;
    		console.log(viewWidth);
    		document.body.appendChild( container );
    		camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 5000 );
    		camera.position.z = 1000;
    		scene = new THREE.Scene();
    		scene.background = new THREE.Color( 0x000000 );
    		var material = new THREE.SpriteMaterial( {
    			map: new THREE.CanvasTexture( generateSprite() ),
    			blending: THREE.AdditiveBlending
    		} );
    		for ( var i = 0; i < 1000; i++ ) {
    			particle = new THREE.Sprite( material );
    			initParticle( particle, i * 10 );
    			scene.add( particle );
    		}
    		renderer = new THREE.CanvasRenderer();
    		renderer.setPixelRatio( window.devicePixelRatio );
    		renderer.setSize( window.innerWidth, window.innerHeight );
    		

            let div_bg = window.document.getElementById('div1');
           // let westbg = Ext.getCmp('westbg');

            //container.appendChild( renderer.domElement );
            div_bg.appendChild(renderer.domElement);
    		
    		
    		//stats = new Stats();
    		//container.appendChild( stats.dom );
    		document.addEventListener( 'mousemove', onDocumentMouseMove, false );
    		document.addEventListener( 'touchstart', onDocumentTouchStart, false );
    		document.addEventListener( 'touchmove', onDocumentTouchMove, false );
    		//
    		window.addEventListener( 'resize', onWindowResize, false );
    	}
    	function onWindowResize() {
    		windowHalfX = window.innerWidth / 2;
    		windowHalfY = window.innerHeight / 2;
    		camera.aspect = window.innerWidth / window.innerHeight;
    		camera.updateProjectionMatrix();
    		renderer.setSize( window.innerWidth, window.innerHeight );
    	}
    	function generateSprite() {
    		var canvas = document.createElement( 'canvas' );
    		canvas.width = 16;
    		canvas.height = 16;
    		var context = canvas.getContext( '2d' );
    		var gradient = context.createRadialGradient( canvas.width / 2, canvas.height / 2, 0, canvas.width / 2, canvas.height / 2, canvas.width / 2 );
    		gradient.addColorStop( 0, 'rgba(255,255,255,1)' );
    		gradient.addColorStop( 0.2, 'rgba(0,255,255,1)' );
    		gradient.addColorStop( 0.4, 'rgba(0,0,64,1)' );
    		gradient.addColorStop( 1, 'rgba(0,0,0,1)' );
    		context.fillStyle = gradient;
    		context.fillRect( 0, 0, canvas.width, canvas.height );
    		return canvas;
    	}
    	function initParticle( particle, delay ) {
    		var particle = this instanceof THREE.Sprite ? this : particle;
    		var delay = delay !== undefined ? delay : 0;
    		particle.position.set( 0, 0, 0 );
    		particle.scale.x = particle.scale.y = Math.random() * 32 + 16;
    		new TWEEN.Tween( particle )
    			.delay( delay )
    			.to( {}, 10000 )
    			.onComplete( initParticle )
    			.start();
    		new TWEEN.Tween( particle.position )
    			.delay( delay )
    			.to( { x: Math.random() * 4000 - 2000, y: Math.random() * 1000 - 500, z: Math.random() * 4000 - 2000 }, 10000 )
    			.start();
    		new TWEEN.Tween( particle.scale )
    			.delay( delay )
    			.to( { x: 0.01, y: 0.01 }, 10000 )
    			.start();
    	}
    	//
    	function onDocumentMouseMove( event ) {
    		mouseX = event.clientX - windowHalfX;
    		mouseY = event.clientY - windowHalfY;
    	}
    	function onDocumentTouchStart( event ) {
    		if ( event.touches.length == 1 ) {
    			event.preventDefault();
    			mouseX = event.touches[ 0 ].pageX - windowHalfX;
    			mouseY = event.touches[ 0 ].pageY - windowHalfY;
    		}
    	}
    	function onDocumentTouchMove( event ) {
    		if ( event.touches.length == 1 ) {
    			event.preventDefault();
    			mouseX = event.touches[ 0 ].pageX - windowHalfX;
    			mouseY = event.touches[ 0 ].pageY - windowHalfY;
    		}
    	}
    	//
    	function animate() {
    		requestAnimationFrame( animate );
    		render();
    		//stats.update();
    	}
    	function render() {
    		TWEEN.update();
    		camera.position.x += ( mouseX - camera.position.x ) * 0.05;
    		camera.position.y += ( - mouseY - camera.position.y ) * 0.05;
    		camera.lookAt( scene.position );
    		renderer.render( scene, camera );
    	}
    	
    	
    	
    	
    	
    },





    旋转几何:function() {

        // console.log("this is showAnimation funciton run.... ");
        var camera, scene, renderer;
        var geometry, group;
        var mouseX = 0,
            mouseY = 0;
        var windowHalfX = 500;
        var windowHalfY = 500;
        var viewWidth = window.innerWidth;
        var viewHeight = window.innerHeight;
        // renderer.setSize(window.innerWidth, window.innerHeight);
        // var windowHalfX = window.innerWidth / 2;
        // var windowHalfY = window.innerHeight / 2;
        init();
        animate();

        function init() {
        	
        	container = document.createElement( 'div' );
//    		viewWidth=container.width;
//    		viewHeight=container.height;
//    		console.log(viewWidth);
    		document.body.appendChild( container );
            camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 1, 10000);
            camera.position.z = 500;
            scene = new THREE.Scene();
            scene.background = new THREE.Color(0xffffff);
            scene.fog = new THREE.Fog(0xffffff, 1, 10000);
            var geometry = new THREE.BoxBufferGeometry(100, 100, 100);
            var material = new THREE.MeshNormalMaterial();
            group = new THREE.Group();
            for (var i = 0; i < 1000; i++) {
                var mesh = new THREE.Mesh(geometry, material);
                mesh.position.x = Math.random() * 2000 - 1000;
                mesh.position.y = Math.random() * 2000 - 1000;
                mesh.position.z = Math.random() * 2000 - 1000;
                mesh.rotation.x = Math.random() * 2 * Math.PI;
                mesh.rotation.y = Math.random() * 2 * Math.PI;
                mesh.matrixAutoUpdate = false;
                mesh.updateMatrix();
                group.add(mesh);
            }
            scene.add(group);
            //
            renderer = new THREE.WebGLRenderer({
                antialias: true
            });
            renderer.setPixelRatio(window.devicePixelRatio);
            renderer.setSize(viewWidth, viewHeight);
            

            let div_bg = window.document.getElementById('div1');
            //let westbg = Ext.getCmp('westbg');

            div_bg.appendChild(renderer.domElement);
            //  Ext.DomHelper.i
            document.addEventListener('mousemove', onDocumentMouseMove, false);
            //
            window.addEventListener('resize', onWindowResize, false);

        }

        function onWindowResize() {
            windowHalfX = window.innerWidth / 2;
            windowHalfY = window.innerHeight / 2;
            camera.aspect = window.innerWidth / window.innerHeight;
            camera.updateProjectionMatrix();
            renderer.setSize(window.innerWidth, window.innerHeight);
        }

        function onDocumentMouseMove(event) {
            mouseX = (event.clientX - windowHalfX) * 10;
            mouseY = (event.clientY - windowHalfY) * 10;
        }
        //
        function animate() {
            requestAnimationFrame(animate);
            render();
            //  stats.update();
        }

        function render() {
            var time = Date.now() * 0.001;
            var rx = Math.sin(time * 0.7) * 0.5,
                ry = Math.sin(time * 0.3) * 0.5,
                rz = Math.sin(time * 0.2) * 0.5;
            camera.position.x += (mouseX - camera.position.x) * 0.05;
            camera.position.y += (-mouseY - camera.position.y) * 0.05;
            camera.lookAt(scene.position);
            group.rotation.x = rx;
            group.rotation.y = ry;
            group.rotation.z = rz;
            renderer.render(scene, camera);
        }

      

    },


    
    afterRender: function() {
    	
    	//随机显示开场动画
    	let flag=new Date();
    	flag%2?this.旋转几何():this.流浪星际();
     
    },





});