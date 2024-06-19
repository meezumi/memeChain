## memeChain (BlockChain) with Java ##

### to use Google's Gson Library for the block Chain: ###
> https://intellij-support.jetbrains.com/hc/en-us/community/posts/206813395-using-gson

### What this BlockChain can do, as of 17th June ###
> 1. made up of blocks that store data.
> 2. has a digital signature that chains your blocks together.
> 3. requires proof of work mining to validate new blocks.
> 4. can be checked to see if data in has is valid and unchanged.

### Installing the BouncyCastle library: ###
> https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk18on/1.78.1/bcprov-jdk18on-1.78.1.jar

### What this BlockChain can do, as of 19th June ###
> 1. allow users to create wallets with new Wallet() method.
> 2. provides these created wallets with public and private keys using Elliptic-Curve cryptography.
> 3. hence, secures the funds, by digital signature algorithm (ECDSA) to prove ownership.
> 4. lastly, allow users to make transactions on the memeChain blockChain with Block.addTransaction() method. an example could be

``` Block.addTransaction(walletB.sendFunds(walletA.publicKey, 30));  ```

#### Check notes.txt for detailed information about the steps and code. ####




