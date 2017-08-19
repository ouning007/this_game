#pragma strict
var explosionSound:AudioClip;


function Start () {
    AudioSource.PlayClipAtPoint(explosionSound, Vector3 (0, 0, -5));
}


function OnCollisionEnter(collision : Collision) {
	if(collision.gameObject.tag=="plane"){
		Destroy(gameObject);
	}
}

function Update () {
  if(transform.position.x<-8 || transform.position.y>9)
	Destroy(gameObject);  
}
