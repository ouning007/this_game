var bombSound:AudioClip;

function Start () {
   AudioSource.PlayClipAtPoint(bombSound, Vector3 (0, 0, -5));
  
}

function Update () {

}


function OnTriggerEnter (other : Collider) {
	
	if(other.tag == "ground")
	{
	Destroy(gameObject);
	}
}