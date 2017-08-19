using UnityEngine;
using System.Collections;

public class BombController : MonoBehaviour {

	public AudioClip bombSound;
	public GameObject explosion;
	private GameObject mytank;
	
	
	
	// Use this for initialization
	void Start () {
	AudioSource.PlayClipAtPoint(bombSound,new Vector3(0,1,-5));
	}
	
	void OnTriggerEnter(Collider other)
	{
		if(other.tag == "ground")
		{
			Instantiate(explosion,new Vector3(transform.position.x,
			       transform.position.y+0.5f,-1f),Quaternion.identity);
			Destroy(gameObject);
		}
	}
	void OnCollisionEnter(Collision collision)
	{
		if(collision.gameObject.tag == "tank")
		{ 
        	Instantiate(explosion,new Vector3(transform.position.x,
		                    transform.position.y+0.5f,-1f),Quaternion.identity);
			Destroy(gameObject);
         
            mytank=GameObject.FindWithTag("tank0");
			mytank.transform.position=new Vector3(10,mytank.transform.position.y,
			                          mytank.transform.position.z);
			//UIManager.score++;
			if(ConnectServer._instance.player==1){
			string msg="score";
			byte[] send_buffer = System.Text.Encoding.UTF8.GetBytes (msg);
			ConnectServer._instance.client.Send (send_buffer);
			}
		}
		if (collision.gameObject.tag == "bian") {
			Instantiate(explosion,new Vector3(transform.position.x,
				transform.position.y+0.5f,-1f),Quaternion.identity);
			Destroy(gameObject);	
		}

		if (collision.gameObject.tag == "tankshell") {
			Instantiate(explosion,new Vector3(transform.position.x,
				transform.position.y+0.5f,-1f),Quaternion.identity);
			Destroy(gameObject);	
			Destroy (collision.gameObject);
		}
             	


	}
}
