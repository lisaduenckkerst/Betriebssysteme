class LivelockTest {
    static class Ressource {
        private ThreadA owner;
        public Ressource(ThreadA d) { owner = d; }
        public synchronized void setOwner(ThreadA d) { owner = d; }
        public synchronized void use() {
            System.out.printf("%s has eaten!", owner.name);
        }
    }

    static class ThreadA implements Runnable {
        private String name;
        private boolean isLocked;

        Ressource r;

        ThreadA otherThread;

        public ThreadA (String n) {
            this.name = n;
            this.isLocked = false;
        }

        public void setRessource(Ressource r) {
            this.r = r;
        }

        public void setOtherThread(ThreadA otherThread) {
            this.otherThread = otherThread;
        }
        
        public String getName() { return name; }
        public boolean isLocked() { return isLocked; }


        @Override
        public void run() {

            while (!this.isLocked) {
                // wenn dieser Thread das Lock nicht hat, warte auf den anderen Thread
                if (r.owner != this) {
                    try { Thread.sleep(1); }
                    catch(InterruptedException e) { continue; }
                    continue;
                }

                // wenn anderer Thread das Lock noch nicht hat
                if (!otherThread.isLocked()) {
                    System.out.println(this.name + ": Du bist zuerst dran, " + otherThread.name + "!");
                    r.setOwner(otherThread);
                    continue;
                }

                r.use();
                this.isLocked = true;
                r.setOwner(otherThread);
            }
        }
    }

    public static void main(String[] args) {
        final ThreadA t1 = new ThreadA("T1");
        final ThreadA t2 = new ThreadA("T2");

        final Ressource s = new Ressource(t1);

        t1.setRessource(s);
        t1.setOtherThread(t2);
        t2.setRessource(s);
        t2.setOtherThread(t1);

       new Thread(t1).start();
       new Thread(t2).start();

    }
}

