using UnityEngine;
using System.Collections;


public class planeController : MonoBehaviour {
	private float speed = 2.0f;
	public  Transform bome;
	//public GameObject player1,player2;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {

		if (ConnectServer._instance.recive_instruction.Length < 4)
			return;

		if ((ConnectServer._instance.recive_instruction [0] == "1"&&this.transform.name=="feiji")||(ConnectServer._instance.recive_instruction [0] == "2"&&this.transform.name=="feiji2")) {
			string[] msg = ConnectServer._instance.recive_instruction;

			if (msg [1] == "1") {
				this.transform.Translate (Vector3.right * speed * Time.deltaTime);
			} else if (msg [1] == "-1") {
				this.transform.Translate (2 * Vector3.left * speed * Time.deltaTime);
			}

			this.transform.Translate (int.Parse (msg [2]) * Vector3.up * speed * Time.deltaTime);
				
			if (msg [3] == "1") {
				Instantiate (bome, this.transform.position - new Vector3 (0f, 0.05f, 0f), this.transform.rotation);	
			} 

			for (int i = 1; i <= 3; i++)
				msg [i] = "0";

		} 

	/*	else if (ConnectServer._instance.recive_instruction [0] == "2") {
			string[] msg = ConnectServer._instance.recive_instruction;

			if (msg [1] == "1") {
				player2.transform.Translate (Vector3.right * speed * Time.deltaTime);
			} else if (msg [1] == "-1") {
				player2.transform.Translate (2 * Vector3.left * speed * Time.deltaTime);
			}

			player2.transform.Translate (int.Parse (msg [2]) * Vector3.up * speed * Time.deltaTime);

			if (msg [3] == "1") {
				Instantiate (bome, player2.transform.position - new Vector3 (0f, 0.05f, 0f), player2.transform.rotation);	
			} 

			for (int i = 1; i <= 3; i++)
				msg [i] = "0";

		}
*/


	}



}
