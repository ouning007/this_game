var explosionSound:AudioClip;

function Start () {
   AudioSource.PlayClipAtPoint(explosionSound, Vector3 (0, 0, -5));
}