using UnityEngine;
using System.Collections;

public class TankShell : MonoBehaviour
 {
  public AudioClip explosionSound;
  public GameObject explosion;


	// Use this for initialization
	void Start ()
	{
	AudioSource.PlayClipAtPoint(explosionSound,new Vector3(0,0,-5));

	}
	
	// Update is called once per frame
	void Update () 
	{
	if(transform.position.x<-8 || transform.position.y>9)
		Destroy(gameObject);
	}
	void OnCollisionEnter(Collision collision)
	{
		if (collision.gameObject.tag == "plane") {
			Instantiate (explosion, new Vector3 (transform.position.x, transform.position.y, -1f), Quaternion.identity);
			Destroy (gameObject);
			Destroy (collision.gameObject);
			//gameControl.over = true;

		} else if (collision.gameObject.tag == "bian") {
			Instantiate (explosion, new Vector3 (transform.position.x, transform.position.y, -1f), Quaternion.identity);
			Destroy (gameObject);
		}

	}


}
