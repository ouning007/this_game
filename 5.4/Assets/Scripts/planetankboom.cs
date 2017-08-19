using UnityEngine;
using System.Collections;

public class planetankboom : MonoBehaviour {
	public Transform explosion,explosion2;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnCollisionEnter(Collision collision){
		if (collision.gameObject.tag == "tank") {
			Instantiate (explosion, new Vector3 (transform.position.x, transform.position.y, -1f), Quaternion.identity);
			Destroy (gameObject);
			//Destroy (collision.gameObject);
			GameObject mytank=GameObject.FindWithTag("tank0");
			mytank.transform.position=new Vector3(10,mytank.transform.position.y,
				mytank.transform.position.z);
			gameControl.over = true;		
		}
	}
}
